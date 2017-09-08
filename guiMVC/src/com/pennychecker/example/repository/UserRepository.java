/**
 * Copyright [2011] Steffen Kämpke
 * mailto: sk@pennychecker.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pennychecker.example.repository;

import com.google.inject.Inject;
import com.pennychecker.example.mvp.model.User;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Steffen Kämpke
 */
public final class UserRepository {

    private final List<User> users = new ArrayList<User>();

    @Inject
    public UserRepository() {
    }

    public void save(User user) throws Exception {
        assert null != user;

        if (null == user.getFirstname()) {
            throw new Exception("Firstname is null.");
        }

        if (null == user.getLastname()) {
            throw new Exception("Lastname is null.");
        }
        
        if ( null == user.getId()) {
            final UID uid = new UID();
            user.setId(uid.toString());
        }

        if (users.contains(user)) {
            final int index = users.indexOf(user);
            users.set(index, user);
        } else {
            users.add(user);
        }
        
        
    }

    public void remove(User user) throws Exception{
        if (users.contains(user)) {
            users.remove(user);
        }
    }

    public List<User> finaAll() throws Exception{
        return Collections.unmodifiableList(users);
    }
}

