plugins {
    id("java")
}

group = "org.otag.hellobd.admintui"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.springframework.security:spring-security-crypto:6.1.4")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}