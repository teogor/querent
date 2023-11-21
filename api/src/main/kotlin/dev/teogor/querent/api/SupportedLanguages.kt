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
 * Interface for representing supported languages.
 *
 * This interface provides methods for managing the list of supported languages.
 */
interface SupportedLanguages {

  /**
   * Adds a language family to the list of supported languages.
   *
   * The language family to add to the list
   * of supported languages.
   */
  operator fun LanguageFamily.unaryPlus()

  /**
   * Returns an array of supported language families.
   *
   * @return An array of supported language families.
   */
  fun getLanguages(): Array<LanguageFamily>
}
