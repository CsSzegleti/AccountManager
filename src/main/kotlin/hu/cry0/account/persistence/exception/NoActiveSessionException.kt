package hu.cry0.account.persistence.exception

class NoActiveSessionException : RuntimeException {
    constructor(msg: String) : super(msg)
    constructor(msg: String, ex: Throwable) : super(msg, ex)
}