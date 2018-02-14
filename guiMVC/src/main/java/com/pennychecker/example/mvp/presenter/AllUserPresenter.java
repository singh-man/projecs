/**
 * Copyright [2011] Steffen KÃ¤mpke
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
package com.pennychecker.example.mvp.presenter;

import com.google.inject.Inject;
import com.pennychecker.eventbus.EventBus;
import com.pennychecker.example.mvp.model.User;
import com.pennychecker.example.event.EditUserEvent;
import com.pennychecker.example.event.UserChangedEvent;
import com.pennychecker.example.event.UserChangedEventHandler;
import com.pennychecker.example.repository.UserRepository;
import com.pennychecker.presenter.event.PresenterRevealedEvent;
import com.pennychecker.presenter.swing.SwingDisplay;
import com.pennychecker.presenter.swing.SwingPresenter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Steffen KÃ¤mpke
 */
public final class AllUserPresenter extends SwingPresenter<AllUserPresenter.Display> {

    private final List<User> users = new ArrayList<User>();
    private User selectedUser;
    private final UserRepository userRepository;

    /**
     * 
     * @param display
     * @param eventBus
     * @param userRepository 
     */
    @Inject
    public AllUserPresenter(Display display, EventBus eventBus, UserRepository userRepository) {
        super(display, eventBus);
        assert null != userRepository;
        this.userRepository = userRepository;
        bind();
        fetchUsers();
    }

    @Override
    protected void onBind() {

        display.getUserList().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int index = display.getSelectedUserIndex();
                if (index < 0) {
                    return;
                }
                selectedUser = users.get(index);
                display.setSelectedUser(selectedUser);
            }
        });

        display.getUserList().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = display.getSelectedUserIndex();
                    if (index < 0) {
                        return;
                    }
                    final User selectedUser = users.get(index);
                    display.setSelectedUser(selectedUser);

                    eventBus.fireEvent(new EditUserEvent(selectedUser));
                }
            }
        });

        registerHandler(eventBus.addHandler(UserChangedEvent.TYPE, new UserChangedEventHandler() {

            public void onUserChangedEvent(UserChangedEvent event) {

                User user = event.getUser();

                if (null == user) {
                    firePresenterRevealedEvent();
                    return;
                }

                if (!users.contains(user)) {
                    users.add(user);
                }

                selectedUser = user;
                display.setSelectedUser(selectedUser);
                display.setUsers(users);
                display.setSelection(users.indexOf(user));
                firePresenterRevealedEvent();
            }
        }));


        display.getAddUserButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                eventBus.fireEvent(new EditUserEvent(null));
            }
        });
        
        display.getEditUserButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if ( null == selectedUser ) {
                    display.setWarning("Please select a user.");
                    return;
                }
                
                eventBus.fireEvent(new EditUserEvent(selectedUser));
            }
        });
        
        display.getRemoveUSerButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                if ( null == selectedUser ) {
                    display.setWarning("Select a user.");
                    return;
                }
                users.remove(selectedUser);
                selectedUser = null;
                display.clearSelections();
                display.setUsers(users);
                display.setSelectedUser(selectedUser);
            }
        });
    }

    @Override
    protected void onUnbind() {
    }

    public void refreshDisplay() {
    }

    private void fetchUsers() {
        users.clear();
        final List<User> userList;
        try {
            userList = userRepository.finaAll();
        } catch (Exception ex) {
            display.setError("Could not fetch users.");
            return;
        }

        display.setUsers(userList);
        display.clearSelections();
    }

    public interface Display extends SwingDisplay {

        JList getUserList();

        JButton getAddUserButton();
        
        JButton getEditUserButton();
        
        JButton getRemoveUSerButton();

        public int getSelectedUserIndex();

        public void setSelectedUser(User selectedUser);

        public void setUsers(List<User> users);

        public void clearSelections();

        public void setSelection(int index);

        public void setWarning(String string);

        public void setError(String string);
    }

    public void firePresenterRevealedEvent() {
        eventBus.fireEvent(new PresenterRevealedEvent(AllUserPresenter.this));
    }
}

