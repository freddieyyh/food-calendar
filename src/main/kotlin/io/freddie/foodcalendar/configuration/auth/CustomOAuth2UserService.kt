package io.freddie.foodcalendar.configuration.auth

import io.freddie.foodcalendar.configuration.auth.dto.CustomOAuth2User
import io.freddie.foodcalendar.configuration.auth.dto.OAuthAttributes
import io.freddie.foodcalendar.domain.user.entity.User
import io.freddie.foodcalendar.domain.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val defaultOAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = defaultOAuth2UserService.loadUser(userRequest)
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint
            .userNameAttributeName

        val oAuthAttributes = OAuthAttributes.ofNaver(oAuth2User.attributes)
        val user = saveOrUpdate(oAuthAttributes)

        return CustomOAuth2User(user, oAuthAttributes.attributes.toMutableMap(), oAuth2User.authorities)
    }

    private fun saveOrUpdate(oAuthAttributes: OAuthAttributes): User {
        val user = userRepository.findByEmail(oAuthAttributes.email)
            ?.apply {
                gender = oAuthAttributes.gender.toEntityGender()
                ageGroup = oAuthAttributes.ageGroup
            } ?: oAuthAttributes.toUserEntity()

        return userRepository.save(user)
    }
}