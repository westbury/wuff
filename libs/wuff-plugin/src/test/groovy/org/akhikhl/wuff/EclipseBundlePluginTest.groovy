/*
 * wuff
 *
 * Copyright 2014  Andrey Hihlovskiy.
 *
 * See the file "LICENSE" for copying and usage permission.
 */
package org.akhikhl.wuff

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 *
 * @author akhikhl
 */
class EclipseBundlePluginTest extends Specification {

  private Project project
  private EclipseBundlePlugin plugin

  def setup() {
    project = ProjectBuilder.builder().build()
    plugin = new EclipseBundlePlugin()
  }

  def 'supports eclipse bundle definition'() {
  when:
    project.apply(plugin: 'java')
    project.repositories {
      mavenLocal()
      mavenCentral()
    }
    plugin.apply(project)
    project.evaluate()
  then:
    project.extensions.findByName('wuff')
    project.wuff.effectiveConfig.defaultEclipseVersion == '4.3'
    project.configurations.findByName('privateLib')
    project.configurations.findByName('compile')
    project.configurations.compile.dependencies.find { it.name.startsWith('org.eclipse.swt') }
    project.configurations.compile.dependencies.find { it.name.startsWith("org.eclipse.swt.${PlatformConfig.current_os_suffix}.${PlatformConfig.current_arch_suffix}") }
    project.configurations.compile.dependencies.find { it.name.startsWith('org.eclipse.jface') }
    project.configurations.compile.dependencies.find { it.name.startsWith('org.eclipse.ui') }
  }
}

