package de.danielglaser.himbeerheim.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import de.danielglaser.himbeerheim.R
import de.danielglaser.himbeerheim.model.ButtonCommand
import de.danielglaser.himbeerheim.model.Data

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityFragment.OnButtonCommandSelectedListener, EditCommandFragment.OnBackToMainSelectedListener {

    companion object {
        lateinit var contextOfApplication: Context
    }

    lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contextOfApplication = applicationContext
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        data = Data()

        if (data.buttonCommands.size == 0) {
            // Erste Buttons hinzufügen
            data.buttonCommands.add(ButtonCommand("Licht An", "sudo ./send 01111 4 1"))
            data.buttonCommands.add(ButtonCommand("Licht Aus", "sudo ./send 01111 4 0"))
            data.buttonCommands.add(ButtonCommand("TV An", "sudo ./send 01110 3 1"))
            data.buttonCommands.add(ButtonCommand("TV Aus", "sudo ./send 01110 3 0"))
            data.buttonCommands.add(ButtonCommand("Raspberry Aus", "sudo shutdown -h 0"))
        }

        loadMainActivityFragment()
    }

    private fun loadMainActivityFragment() {
        val manager = supportFragmentManager
        manager.popBackStack()
        val newFragment = MainActivityFragment(data)

        val trans = manager.beginTransaction()
        trans.replace(R.id.fragment_container, newFragment)
        trans.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onButtonCommandSelected(buttonCommand: ButtonCommand) {
        val manager = supportFragmentManager
            val newFragment = EditCommandFragment(buttonCommand)

            val trans = manager.beginTransaction()
            trans.addToBackStack(null)
            trans.replace(R.id.fragment_container, newFragment)
            trans.commit()
    }

    override fun onSaveSelectedListener() {
        data.save()
        loadMainActivityFragment()
    }

    override fun onCancelSelectedListener() {
        loadMainActivityFragment()
    }

    override fun onDeleteSelectedListener(buttonCommand: ButtonCommand) {
        data.buttonCommands.remove(buttonCommand)
        data.save()
        loadMainActivityFragment()
    }
}
