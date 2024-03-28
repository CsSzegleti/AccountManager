package hu.cry0.account.service.exception

class NotFoundException : Exception {
    constructor(msg: String?) : super(msg)
    constructor(msg: String?, ex: Throwable) : super(msg, ex)
}