package com.bumble.livemosaic.participant

actual fun prepareTimeZones() {
    jsJodaTz
}

@JsModule("@js-joda/timezone")
@JsNonModule
external object JsJodaTimeZoneModule

private val jsJodaTz = JsJodaTimeZoneModule
