package hu.cry0.account

import hu.cry0.account.config.BankProperty
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextProvider : ApplicationContextAware {

    companion object {

        private var appContext: ApplicationContext? = null

        val bankProperty: BankProperty?
            get() = appContext?.getBean(BankProperty::class.java)

        fun getApplicationContext() = appContext

    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        appContext = applicationContext
    }
}