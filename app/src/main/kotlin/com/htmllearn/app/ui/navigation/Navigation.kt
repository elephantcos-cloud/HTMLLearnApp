package com.htmllearn.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.htmllearn.app.ui.screens.*
import com.htmllearn.app.viewmodel.AppViewModel

sealed class Screen(val route: String) {
    object Onboarding    : Screen("onboarding")
    object Dashboard     : Screen("dashboard")
    object Lessons       : Screen("lessons")
    object LessonDetail  : Screen("lesson/{lessonId}") {
        fun createRoute(id: String) = "lesson/$id"
    }
    object Quiz          : Screen("quiz/{lessonId}") {
        fun createRoute(id: String) = "quiz/$id"
    }
    object CodeEditor    : Screen("code_editor")
    object TagReference  : Screen("tag_reference")
    object Projects      : Screen("projects")
    object ProjectDetail : Screen("project/{projectId}") {
        fun createRoute(id: String) = "project/$id"
    }
    object Profile       : Screen("profile")
    object Settings      : Screen("settings")
    object Achievements  : Screen("achievements")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    viewModel: AppViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToLessons    = { navController.navigate(Screen.Lessons.route) },
                onNavigateToEditor     = { navController.navigate(Screen.CodeEditor.route) },
                onNavigateToReference  = { navController.navigate(Screen.TagReference.route) },
                onNavigateToProjects   = { navController.navigate(Screen.Projects.route) },
                onNavigateToProfile    = { navController.navigate(Screen.Profile.route) },
                viewModel = viewModel
            )
        }

        composable(Screen.Lessons.route) {
            LessonsScreen(
                onLessonClick  = { id -> navController.navigate(Screen.LessonDetail.createRoute(id)) },
                onBackClick    = { navController.popBackStack() },
                viewModel      = viewModel
            )
        }

        composable(
            Screen.LessonDetail.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { back ->
            val lessonId = back.arguments?.getString("lessonId") ?: ""
            LessonDetailScreen(
                lessonId     = lessonId,
                onBack       = { navController.popBackStack() },
                onStartQuiz  = { navController.navigate(Screen.Quiz.createRoute(lessonId)) },
                onOpenEditor = { navController.navigate(Screen.CodeEditor.route) },
                viewModel    = viewModel
            )
        }

        composable(
            Screen.Quiz.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { back ->
            val lessonId = back.arguments?.getString("lessonId") ?: ""
            QuizScreen(
                lessonId  = lessonId,
                onBack    = { navController.popBackStack() },
                onFinish  = { navController.navigate(Screen.Lessons.route) { popUpTo(Screen.Quiz.route) { inclusive = true } } },
                viewModel = viewModel
            )
        }

        composable(Screen.CodeEditor.route) {
            CodeEditorScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.TagReference.route) {
            TagReferenceScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Projects.route) {
            ProjectsScreen(
                onProjectClick = { id -> navController.navigate(Screen.ProjectDetail.createRoute(id)) },
                onBack         = { navController.popBackStack() }
            )
        }

        composable(
            Screen.ProjectDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { back ->
            val projectId = back.arguments?.getString("projectId") ?: ""
            ProjectDetailScreen(
                projectId = projectId,
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack             = { navController.popBackStack() },
                onSettings         = { navController.navigate(Screen.Settings.route) },
                onAchievements     = { navController.navigate(Screen.Achievements.route) },
                viewModel          = viewModel
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack    = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(Screen.Achievements.route) {
            AchievementsScreen(
                onBack    = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
