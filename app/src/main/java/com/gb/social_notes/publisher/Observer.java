package com.gb.social_notes.publisher;

import com.gb.social_notes.repository.CardData;

public interface Observer {
    void receiveMessage(CardData cardData);
}
