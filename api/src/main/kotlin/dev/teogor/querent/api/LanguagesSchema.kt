/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.querent.api

import dev.teogor.xenoglot.LanguageFamily

/**
 * Interface for representing language schema options.
 *
 * This interface provides methods for managing the unqualified
 * resource locale and the list of supported languages.
 */
interface LanguagesSchema {

  /**
   * Gets or sets the unqualified resource locale.
   *
   * The unqualified resource locale is the default language for
   * the project's resources.
   */
  var unqualifiedResLocale: LanguageFamily

  /**
   * Gets the list of supported languages.
   *
   * The list of supported languages represents the languages that
   * the project's resources will be generated for.
   */
  val supportedLanguages: List<String>

  /**
   * Adds supported languages.
   *
   * @param block The configuration block for adding supported
   * languages.
   */
  fun addSupportedLanguages(block: SupportedLanguages.() -> Unit)
}
