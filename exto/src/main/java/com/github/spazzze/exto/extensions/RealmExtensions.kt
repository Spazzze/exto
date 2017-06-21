package com.github.spazzze.exto.extensions

import io.realm.Realm

/**
 * @author Space
 * @date 17.03.2017
 */

fun Realm.auto(function: Realm.() -> Unit) {
    use { function(this) }
}

fun Realm.autoExecuteTransaction(function: Realm.() -> Unit) {
    use { executeTransaction { function(this) } }
}