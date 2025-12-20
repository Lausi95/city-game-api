package de.lausi95.misterx

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
  fromApplication<MisterxApplication>().with(TestcontainersConfiguration::class).run(*args)
}
