/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.cf.taste.impl.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A helper class for implementing {@link Refreshable}. This object is typically included in an implementation
 * {@link Refreshable} to implement {@link Refreshable#refresh(Collection)}. It execute the class's own
 * supplied update logic, after updating all the object's dependencies. This also ensures that dependencies
 * are not updated multiple times.
 */
public final class RefreshHelper implements Refreshable {
  
  private static final Logger log = LoggerFactory.getLogger(RefreshHelper.class);
  
  private final List<Refreshable> dependencies;
  private final ReentrantLock refreshLock;
  private final Callable<?> refreshRunnable;
  
  /**
   * @param refreshRunnable
   *          encapsulates the containing object's own refresh logic
   * 接口其实是极度抽象的类，也可以进行实例化
   */
  public RefreshHelper(Callable<?> refreshRunnable) {
	 //创建长度为3的ArrayList属性的dependencies，用来存储属性值
    this.dependencies = new ArrayList<>(3);
    //这里返回的是一个ReentrantLock对象，内含两个属性：
    //一个是serialVersionUID，通过static定义；一个是not-fair同步锁，如果参数是true，就是fair同步锁
    //仅实现Serializable接口的类可以采用默认的序列化方式
    //SerialVersionUID是用来访问和控制序列化过程的。
    this.refreshLock = new ReentrantLock();
    //貌似啥都没干
    this.refreshRunnable = refreshRunnable;
  }
  
  /** Add a dependency to be refreshed first when the encapsulating object does. */
  public void addDependency(Refreshable refreshable) {
    if (refreshable != null) {
      dependencies.add(refreshable);
    }
  }
  
  public void removeDependency(Refreshable refreshable) {
    if (refreshable != null) {
      dependencies.remove(refreshable);
    }
  }
  
  /**
   * Typically this is called in {@link Refreshable#refresh(java.util.Collection)} and is the entire body of
   * that method.
   */
  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    if (refreshLock.tryLock()) {
      try {
        alreadyRefreshed = buildRefreshed(alreadyRefreshed);
        for (Refreshable dependency : dependencies) {
          maybeRefresh(alreadyRefreshed, dependency);
        }
        if (refreshRunnable != null) {
          try {
            refreshRunnable.call();
          } catch (Exception e) {
            log.warn("Unexpected exception while refreshing", e);
          }
        }
      } finally {
        refreshLock.unlock();
      }
    }
  }
  
  /**
   * Creates a new and empty {@link Collection} if the method parameter is {@code null}.
   *
   * @param currentAlreadyRefreshed
   *          {@link Refreshable}s to refresh later on
   * @return an empty {@link Collection} if the method param was {@code null} or the unmodified method
   *         param.
   */
  public static Collection<Refreshable> buildRefreshed(Collection<Refreshable> currentAlreadyRefreshed) {
    return currentAlreadyRefreshed == null ? new HashSet<Refreshable>(3) : currentAlreadyRefreshed;
  }
  
  /**
   * Adds the specified {@link Refreshable} to the given collection of {@link Refreshable}s if it is not
   * already there and immediately refreshes it.
   * 
   * @param alreadyRefreshed
   *          the collection of {@link Refreshable}s
   * @param refreshable
   *          the {@link Refreshable} to potentially add and refresh
   */
  public static void maybeRefresh(Collection<Refreshable> alreadyRefreshed, Refreshable refreshable) {
    if (!alreadyRefreshed.contains(refreshable)) {
      alreadyRefreshed.add(refreshable);
      log.info("Added refreshable: {}", refreshable);
      refreshable.refresh(alreadyRefreshed);
      log.info("Refreshed: {}", alreadyRefreshed);
    }
  }
}
