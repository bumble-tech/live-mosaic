package com.bumble.livemosaic.participant

@JsModule("@js-joda/timezone")
@JsNonModule
external object JsJodaTimeZoneModule

private val jsJodaTz = JsJodaTimeZoneModule
actual fun prepareTimeZones() {
    jsJodaTz
}
