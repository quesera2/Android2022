include(
    ":app",

    ":core:core-ui",

    ":feature:todo-list",
    ":feature:todo-detail",

    ":data:todo",
)

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "Android2022"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
