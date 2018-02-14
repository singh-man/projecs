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
import com.pennychecker.example.event.EditUserEventHandler;
import com.pennychecker.example.event.UserChangedEvent;
import com.pennychecker.example.repository.UserRepository;
import com.pennychecker.presenter.event.PresenterRevealedEvent;
import com.pennychecker.presenter.swing.SwingDisplay;
import com.pennychecker.presenter.swing.SwingPresenter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Steffen KÃ¤mpke
 */
public final class EditUserPresenter extends SwingPresenter<EditUserPresenter.Display> {

    private User selectedUser;
    private final UserRepository userRepository;

    /**
     * 
     * @param display
     * @param eventBus
     * @param userRepository  
     */
    @Inject
    public EditUserPresenter(Display display, EventBus eventBus, UserRepository userRepository) {
        super(display, eventBus);
        assert null != userRepository;
        this.userRepository = userRepository;
        bind();
    }

    @Override
    protected void onBind() {

        registerHandler(eventBus.addHandler(EditUserEvent.TYPE, new EditUserEventHandler() {

            public void onEditUserEvent(EditUserEvent event) {
                selectedUser = event.getUser();

                if (null == selectedUser) {
                    display.clearForm();
                } else {
                    display.setUser(selectedUser);
                }

                eventBus.fireEvent(new PresenterRevealedEvent(EditUserPresenter.this));
            }
        }));

        display.getSaveButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });

        display.getCloseButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                display.clearForm();
                eventBus.fireEvent(new UserChangedEvent(null));
            }
        });
    }

    @Override
    protected void onUnbind() {
    }

    public void refreshDisplay() {
    }

    public interface Display extends SwingDisplay {

        JButton getSaveButton();

        JButton getCloseButton();

        String getFirstname();

        String getLastName();

        public void setErrorMessage(String string);

        public void clearForm();

        public void setUser(User user);

        public void setWarningMessage(String string);
    }

    private void saveUser() {
        assert null != display.getFirstname();
        assert null != display.getLastName();
        final String firstname = display.getFirstname();
        final String lastname = display.getLastName();

        if (firstname.isEmpty()) {
            display.setWarningMessage("Please insert the firstname.");
            return;
        }

        if (lastname.isEmpty()) {
            display.setWarningMessage("Please insert the lastname.");
            return;
        }
        final User user;

        if (null == selectedUser) {
            user = new User(firstname, lastname);
        } else {
            user = selectedUser;
            user.setFirstname(firstname);
            user.setLastname(lastname);
        }
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            Logger.getLogger(EditUserPresenter.class.getName()).log(Level.SEVERE, null, ex);
            display.setErrorMessage("Could not save the user.");
            return;
        }


        selectedUser = null;
        display.clearForm();

        eventBus.fireEvent(new UserChangedEvent(user));
    }
}

