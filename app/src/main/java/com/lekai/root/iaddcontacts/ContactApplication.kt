package com.lekai.root.iaddcontacts

import android.app.Application
import com.lekai.root.iaddcontacts.repositories.ContactRepo
import com.lekai.root.iaddcontacts.repositories.ContactRepoImpl
import com.lekai.root.iaddcontacts.service.ContactService
import com.lekai.root.iaddcontacts.service.ContactServiceImpl
import com.lekai.root.iaddcontacts.ui.viewModels.ContactViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @author khalidToak
 * @since
 */
class ContactApplication: Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ContactApplication))

        bind<ContactService>() with singleton { ContactServiceImpl(instance()) }
        bind<ContactRepo>() with singleton { ContactRepoImpl(instance()) }
        bind() from singleton {ContactViewModelFactory(instance())}

    }

}