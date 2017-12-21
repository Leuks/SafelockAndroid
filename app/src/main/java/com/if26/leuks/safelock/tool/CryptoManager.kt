package com.if26.leuks.safelock.tool

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.spec.SecretKeySpec
import com.if26.leuks.safelock.tool.Const.Companion.AES_MODE
import com.if26.leuks.safelock.tool.Const.Companion.CHARSET
import com.if26.leuks.safelock.tool.Const.Companion.ivBytes
import java.nio.charset.Charset
import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec


/**
 * Created by leuks on 20/12/2017.
 */
class CryptoManager {
    companion object {

        @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
        private fun generateKey(password: String): SecretKeySpec {
            val digest = MessageDigest.getInstance(Const.HASH_ALGORITHM)
            val bytes = password.toByteArray(charset("UTF-8"))
            digest.update(bytes, 0, bytes.size)
            val key = digest.digest()
            return SecretKeySpec(key, "AES")
        }


        @Throws(GeneralSecurityException::class)
        fun encrypt(password: String, message: String): String {

            try {
                val key = generateKey(password)

                val cipherText = encrypt(key, ivBytes, message.toByteArray(Charset.forName(CHARSET)))

                val encoded = Base64.encodeToString(cipherText, Base64.NO_WRAP)
                return encoded
            } catch (e: UnsupportedEncodingException) {
                throw GeneralSecurityException(e)
            }

        }

        @Throws(GeneralSecurityException::class)
        fun encrypt(key: SecretKeySpec, iv: ByteArray, message: ByteArray): ByteArray {
            val cipher = Cipher.getInstance(AES_MODE)
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
            val cipherText = cipher.doFinal(message)

            return cipherText
        }

        @Throws(GeneralSecurityException::class)
        fun decrypt(password: String, base64EncodedCipherText: String): String {

            try {
                val key = generateKey(password)

                val decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP)

                val decryptedBytes = decrypt(key, ivBytes, decodedCipherText)

                return String(decryptedBytes, Charset.forName(CHARSET))
            } catch (e: UnsupportedEncodingException) {
                throw GeneralSecurityException(e)
            }

        }

        @Throws(GeneralSecurityException::class)
        fun decrypt(key: SecretKeySpec, iv: ByteArray, decodedCipherText: ByteArray): ByteArray {
            val cipher = Cipher.getInstance(AES_MODE)
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            val decryptedBytes = cipher.doFinal(decodedCipherText)

            return decryptedBytes
        }

        private fun bytesToHex(bytes: ByteArray): String {
            val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
            val hexChars = CharArray(bytes.size * 2)
            var v: Int
            for (j in bytes.indices) {
                v = bytes[j].toInt() and (0xFF.toInt())
                hexChars[j * 2] = hexArray[v.ushr(4)]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return String(hexChars)
        }
    }
}