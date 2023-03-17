/**
 * Copyright (c) 2008, SnakeYAML
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package wf.utils.java.file.yamlconfiguration.snakeyaml.tokens;

import wf.utils.java.file.yamlconfiguration.snakeyaml.error.Mark;

/**
 * Anchor
 */
public final class AnchorToken extends Token {

  private final String value;

  /**
   * Anchor
   *
   * @param value - anchor
   * @param startMark - start
   * @param endMark - end
   */
  public AnchorToken(String value, Mark startMark, Mark endMark) {
    super(startMark, endMark);
    this.value = value;
  }

  /**
   * getter
   *
   * @return anchor
   */
  public String getValue() {
    return this.value;
  }

  /**
   * getter
   *
   * @return the identity
   */
  @Override
  public ID getTokenId() {
    return ID.Anchor;
  }
}