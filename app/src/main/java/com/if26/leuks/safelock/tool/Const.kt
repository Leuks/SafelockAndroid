package com.if26.leuks.safelock.tool

/**
 * Created by leuks on 23/11/2017.
 */
class Const {
    companion object {
        val AES_MODE = "AES/CBC/PKCS7Padding"
        val CHARSET = "UTF-8"
        val HASH_ALGORITHM = "SHA-256"
        val ivBytes = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val PASSWORD = "loremipsum"
    }
}