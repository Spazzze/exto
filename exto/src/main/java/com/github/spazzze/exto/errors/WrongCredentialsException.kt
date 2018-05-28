package com.github.spazzze.exto.errors

import java.io.IOException

/**
 * @author Space
 * @date 10.04.2017
 */

class WrongCredentialsException(override val message: String = "") : IOException()