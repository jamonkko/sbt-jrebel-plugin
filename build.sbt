organization := "fi.jamonkko.sbtplugins"

name := "sbt-jrebel-plugin"

version := "1.0.0"

sbtVersion in Global := "1.2.8"

scalaVersion := "2.12.8"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  s"${artifact.name}_${sv.productElement(1)}-${module.revision}.${artifact.extension}"

}

sbtPlugin := true

homepage := Some(url("http://github.com/jamonkko/sbt-jrebel-plugin"))

licenses += ("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

pomExtra := (
  <scm>
    <connection>scm:git:git@github.com:jamonkko/sbt-jrebel-plugin.git</connection>
    <developerConnection>scm:git:git@github.com:jamonkko/sbt-jrebel-plugin.git</developerConnection>
    <url>git@github.com:jamonkko/sbt-jrebel-plugin.git</url>
  </scm>
  <developers>
    <developer>
      <id>jamonkko</id>
      <name>Jarkko Mönkkönen</name>
      <email>jarkko.monkkonen@gmail.com</email>
      <timezone>+2</timezone>
    </developer>
  </developers>
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
