package com.qmt.besedo.repository;

import com.qmt.besedo.model.Message;
import io.vavr.control.Try;

public interface MessageDao {

    Try<Message> injectMessage(Message message);
}
