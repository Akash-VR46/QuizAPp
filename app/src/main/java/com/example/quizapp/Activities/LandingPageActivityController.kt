package com.example.quizapp.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.*
import com.example.quizapp.DataClass.Subjects
import com.example.quizapp.Database.QuizDatabase
import com.example.quizapp.Fragments.ScoreHistoryFragment
import com.example.quizapp.Fragments.SettingsFragment
import com.example.quizapp.Fragments.SubjectChoiceFragment
import com.example.quizapp.Views.LandingPageView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LandingPageActivityController : AppCompatActivity() {

    // lateinit variable declarations for UI elements, database, view, and quiz state
    private lateinit var drawer : DrawerLayout
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar
    private lateinit var navigationDrawer : NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var database: QuizDatabase

    lateinit var landingPageView: LandingPageView
    lateinit var quizType : QuizType
    private lateinit var quizState: QuizState
    private lateinit var sharedPreferences:SharedPreferences

    lateinit var subjectList : ArrayList<Subjects>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.navigation_drawer_main_activity)


        /*
        Getting the previous state of the quiz,
        if any available then continue with that quiz
         */
        sharedPreferences = getSharedPreferences("previousQuizState", MODE_PRIVATE)
        val previousQuizStateJson :String? = sharedPreferences.getString("previousQuizState","")
        val gson = Gson()
        Log.d("json",previousQuizStateJson!!)
        quizState = QuizState()
        if(previousQuizStateJson != ""){

            quizState = gson.fromJson(previousQuizStateJson, QuizState::class.java)
            Log.d("json",previousQuizStateJson)
            quizType = QuizType.getInstance()
            quizState.subject?.let { QuizType.setSubject(it) }
            QuizType.setQuizType(quizState.quizType)
            QuizType.setScore(quizState.score)
            QuizType.setCurrentQuestion(quizState.currentQuestion)
            QuizType.setAttemptedQuestionArray(quizState.attemptedQuestionArray)
        }

        // initialize subject list and landing page view, and get database instance
        subjectList = ArrayList()

        landingPageView = LandingPageView()
        database = QuizDatabase.getDataBase(this)


        // get list of subjects from the database using the QuestionModel class
        val questionModel = QuestionModel()
        questionModel.getListOfSubjects(this)




        // insert the subject list into the database using coroutines
        GlobalScope.launch {
            database.subjectDao().insertAll(subjectList)
        }


        /*
        setting navigation drawer
         */
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navigationDrawer =findViewById(R.id.nvView)
        drawer = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(this,drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationDrawer)


        /*
        checking user already selected the quiz type and quiz subject
         */
//        Log.d("QuizType",quizState.quizType)
//        Log.d("QuizSub",quizState.subject.toString())
        if(quizState.quizType != "Default" || quizState.subject != null){
            // If user has already selected the quiz type and quiz subject, then start the QuizActivity
            val intent = Intent(this, QuizActivityController::class.java)
            startActivity(intent)
        }
        else{
            // If user has not selected the quiz type and quiz subject, then show the SubjectChoiceFragment
            val subjectFragment = SubjectChoiceFragment()
            val subjectFragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
            subjectFragmentTransition.replace(R.id.flContent,subjectFragment).commit()
        }

    }
    /*
    setting drawer contents
     */
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }
    /*
    switching of fragments(settings,subjectChoice,history),
     based on user selection from the navigation drawer
     */
    private fun selectDrawerItem(menuItem: MenuItem){
        var fragment: Fragment? = null
        when(menuItem.itemId){
            R.id.nav_home -> fragment = SubjectChoiceFragment()
            R.id.nav_setting ->fragment = SettingsFragment()
            R.id.nav_history -> fragment = ScoreHistoryFragment()

        }
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.flContent, fragment!!)
        fragmentTransaction.commit()

        menuItem.isChecked = true
        title = menuItem.title;
        drawer.closeDrawers();

    }
    /*

     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /*
        Saving the current state of the quiz in shared preference
     */
    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val savingState = QuizState()
        savingState.quizType = QuizType.getQuizType()
        savingState.subject = QuizType.getSubject()
        val json = gson.toJson(savingState)
        editor.putString("previousQuizState", json)
        editor.apply()
    }
}