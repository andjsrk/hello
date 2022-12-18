import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "1.6.21"
    id("org.jetbrains.dokka") version "1.6.21"
}

group = "io.github.andjsrk"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = rootProject.name
            from(project.components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            repositories {
                maven {
                    name = "central"
                    url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                    credentials.runCatching {
                        val nexusUsername: String by project
                        val nexusPassword: String by project
                        username = nexusUsername
                        password = nexusPassword
                    }
                }
            }

            pom {
                name.set(rootProject.name)
                description.set("description")
                url.set("https://github.com/andjsrk/hello")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("andjsrk")
                        name.set("andjsrk")
                        email.set("andjsrk0213@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/andjsrk/hello")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(publishing.publications)
}
