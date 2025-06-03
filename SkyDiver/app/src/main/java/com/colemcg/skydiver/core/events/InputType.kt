package com.colemcg.skydiver.core.events

/**
 * Enumeration of supported input types.
 * this is used to determine the type of input event that occurred.
 * this is necessary because we will be using the same event class for both iOS and Android,
 * and we need to know the type of input event to handle it correctly, without access to platform specific code.
 * 
 * @author Cole McGregor
 */
enum class InputType {
    Tap,
    Drag,
    Hold
}
