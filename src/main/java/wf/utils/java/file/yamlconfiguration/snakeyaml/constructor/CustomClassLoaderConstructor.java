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
package wf.utils.java.file.yamlconfiguration.snakeyaml.constructor;

/**
 * Construct instances with a custom Class Loader.
 */
public class CustomClassLoaderConstructor extends Constructor {

  private final ClassLoader loader;

  /**
   * Create
   *
   * @param cLoader the class loader to find the class definition
   */
  public CustomClassLoaderConstructor(ClassLoader cLoader) {
    this(Object.class, cLoader);
  }

  /**
   * Create
   *
   * @param theRoot - the class to instantiate
   * @param theLoader - the class loader to find the class definition
   */
  public CustomClassLoaderConstructor(Class<? extends Object> theRoot, ClassLoader theLoader) {
    super(theRoot);
    if (theLoader == null) {
      throw new NullPointerException("Loader must be provided.");
    }
    this.loader = theLoader;
  }

  /**
   * Load the class
   *
   * @param name - the name
   * @return Class to create
   * @throws ClassNotFoundException - when cannot load the class
   */
  @Override
  protected Class<?> getClassForName(String name) throws ClassNotFoundException {
    return Class.forName(name, true, loader);
  }
}
