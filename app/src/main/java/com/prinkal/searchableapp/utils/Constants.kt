package com.prinkal.searchableapp.utils

import android.net.Uri
import java.io.File

// Bundle keys
const val PID = "pid"
const val PACKAGE_NAME = "package_name"
const val DATA = "data"
const val KEY_QUERY = "query"

const val ID = "id"
const val TITLE = "title"
const val DESC = "description"
const val PHOTO_URL = "photo_url"

val CONTACT_COLUMNS = arrayOf(
    ID, TITLE, DESC, PHOTO_URL
)

/**
 * Authority name can be of any constant String.
 */
const val AUTHORITY_NAME = "ContentProviderSample.Contacts"

/**
 * Base name can be of any constant String
 */
private const val BASE_NAME = "iDoAndroid"

/**
 * Uri to retrieve all sampledata
 */
val CONTACTS = Uri.parse(
    "content://" + AUTHORITY_NAME + File.separator
            + BASE_NAME + "contacts"
)