package com.gb.social_notes.repository;

import java.util.List;

public interface CardsSource {
    int size();

    List<CardData> getAllCardsData();

    CardData getCardData(int position);

    void clearCardsData();

    void addCardData(CardData cardData);

    void deleteCardData(int position);

    void updateCardData(int position, CardData newCardData);
}
