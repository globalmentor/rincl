/*
 * Copyright © 2016 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rincl.resourcebundle;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.*;

/**
 * Strategy for determining the parent class priority when creating resolving parent resources.
 * @author Garret Wilson
 */
@FunctionalInterface
public interface ResolvingClassStrategy {

	/**
	 * The default resolving class strategy, which returns the class, parent classes, and interfaces following these rules:
	 * <ul>
	 * <li>The given context class itself has highest priority.
	 * <li>The {@link Object} superclass is not included.</li>
	 * <li>Subclasses have priority over superclasses.</li>
	 * <li>Classes (both abstract and concrete) have priority over interfaces.
	 * <li>An interface and its parents declared for a subclass has priority over a interface and its parents declared for a superclass, even for an interface
	 * and/or its parents redeclared for a subclass.</li>
	 * <li>At each level interfaces are returned in a breadth-first traversal, in the order they are declared.</li>
	 * </ul>
	 */
	public static final ResolvingClassStrategy DEFAULT = contextClass -> {
		//collect the class and all its parent classes (except Object) in order
		final List<Class<?>> parentClasses = new ArrayList<>();
		do {
			parentClasses.add(contextClass);
			contextClass = contextClass.getSuperclass();
		} while(contextClass != null && !contextClass.equals(Object.class));
		//keep track of the order of resolving classes but prevent duplicates
		//the parent classes we collected take priority
		final Set<Class<?>> resolvingClasses = new LinkedHashSet<>(parentClasses);
		//at every level, re-use a queue (which we will drain each time) for the interfaces
		final Queue<Class<?>> interfaceQueue = new LinkedList<>();
		//add the interfaces at every level
		for(final Class<?> parentClass : parentClasses) {
			//add all the declared interfaces and their parents, in breadth-first order
			Collections.addAll(interfaceQueue, parentClass.getInterfaces());
			while(!interfaceQueue.isEmpty()) {
				final Class<?> interfaceClass = interfaceQueue.remove();
				//add this interface to our resolving list
				resolvingClasses.add(interfaceClass);
				//put its interfaces back into the queue for processing when we get to the next level
				Collections.addAll(interfaceQueue, interfaceClass.getInterfaces());
			}
			assert interfaceQueue.isEmpty();
		}
		return resolvingClasses.stream();
	};

	/** A resolving class strategy that only resolves resources for the class itself and no parent classes. */
	public static final ResolvingClassStrategy NO_ANCESTORS = Stream::of;

	/**
	 * Determines the priority of resource classes when determining parent resources.
	 * <p>
	 * Normally the resolution includes the class itself and some order of its parent classes and/or interfaces.
	 * </p>
	 * @param contextClass The class for which resolving classes should be determined.
	 * @return The classes for resolving resources for the given context class.
	 */
	public @Nonnull Stream<Class<?>> resolvingClasses(@Nonnull Class<?> contextClass);

}
