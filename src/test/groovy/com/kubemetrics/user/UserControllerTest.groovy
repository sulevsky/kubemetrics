package com.kubemetrics.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class UserControllerTest extends Specification {

    @Autowired
    UserRepository userRepository
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper

    private final static List<FieldDescriptor> requestFieldList = [
            firstName: "User's first name. Not blank",
            lastName : "User's last name. Not blank",
            email    : "User's email. Fits email format"
    ].collect { PayloadDocumentation.fieldWithPath(it.key).description(it.value) }

    private final static List<FieldDescriptor> responseFieldList = requestFieldList +
            PayloadDocumentation.fieldWithPath("id").description("User's id. Not blank")

    void cleanup() {
        userRepository.deleteAll()
    }

    def "should read user by id"() {
        given:
        def testUser = new User(null, "John", "Doe", "johny@mail.com")
        def userId = userRepository.save(testUser).id

        expect: "return if user exists"
        mockMvc.perform(get("/users/{id}", userId))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$.id', is(userId)))
               .andExpect(jsonPath('$.firstName', is("John")))
               .andDo(document("users/read-by-id",
                               responseFields(responseFieldList),
                               pathParameters(parameterWithName("id").description("User's id"))))
        and: "404 if not exist"
        mockMvc.perform(get("/users/{id}", "non-existing-id"))
               .andExpect(status().isNotFound())
    }

    def "should create user"() {
        given:
        def validBody = ["firstName": "Mary",
                         "lastName" : "Jane",
                         "email"    : "mary@mail.com"]

        expect: "proper response"
        mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validBody)))
               .andExpect(status().isCreated())
               .andExpect(header().string(HttpHeaders.LOCATION, Matchers.startsWith("/users/")))
               .andDo(document("users/create",
                               requestFields(requestFieldList)))

        and: "exist in db"
        def allUsers = userRepository.findAll()
        allUsers.size() == 1
        allUsers.every {
            it.id != null &&
                    it.firstName == "Mary" &&
                    it.lastName == "Jane"
        }
        and: "validate input"
        def emptyNameBody = ["firstName": "",
                             "lastName" : "Jane",
                             "email"    : "mary@mail.com"]
        mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emptyNameBody)))
               .andExpect(status().isBadRequest())

        def badEmail = ["firstName": "Anna",
                        "lastName" : "Jane",
                        "email"    : "mail.com"]
        mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(badEmail)))
               .andExpect(status().isBadRequest())
    }
}
