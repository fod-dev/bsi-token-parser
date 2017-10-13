package com.fortify.fod.parser

import org.apache.http.client.utils.URLEncodedUtils
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException

class BsiTokenParser {
    @Throws(URISyntaxException::class, UnsupportedEncodingException::class)
    fun parse(token: String): BsiToken {

        val trimmedToken = token.trim()

        if (trimmedToken.contains("/bsi2.aspx?")) {
            val uri = URI(trimmedToken)
            return parseBsiUrl(uri)
        } else {
            return parseBsiToken(trimmedToken)
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseBsiUrl(uri: URI): BsiToken {

        val params = URLEncodedUtils.parse(uri, "UTF-8")
        val token = BsiToken()

        if (uri.scheme != null && uri.host != null) {
            token.apiUri = "${uri.scheme}://${uri.host}"
        }

        for (param in params) {
            when (param.name) {
                "tid" -> token.tenantId = Integer.parseInt(param.value)
                "tc" -> token.tenantCode = param.value
                "pv" -> token.projectVersionId = Integer.parseInt(param.value)
                "ts" -> token.technologyStack = param.value
                "ll" -> token.languageLevel = param.value
                "astid" -> token.assessmentTypeId = Integer.parseInt(param.value)
                "payloadType" -> token.payloadType = param.value
                "ap" -> token.auditPreference = param.value

/*                if(token.technologyVersion != null){
                    "ll" -> token.technologyVersion = param.value
                }*/


            }
        }

        return token
    }

    private fun parseBsiToken(codedToken: String): BsiToken {

        var bsiBytes = java.util.Base64.getDecoder().decode(codedToken)
        var decodedToken = String(bsiBytes,"UTF-8")

        var token = BsiToken()
        token.tenantId

        return token

    }
}
