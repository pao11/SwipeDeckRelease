package com.pao11.xyi.swipedeck.Utility;

import com.pao11.xyi.swipedeck.CardContainer;

import java.util.LinkedList;

/**
 * Created by pao11 on 21/08/2016.
 */
public class Deck<T extends CardContainer> {

    private LinkedList<T> internal = new LinkedList<>();
    private DeckEventListener listener;

    public Deck(DeckEventListener listener){
        this.listener = listener;
    }

    //public facing methods
    public void addBack(T t){
        this.addLast(t);
    }
    public void addFront(T t){
        this.addFirst(t);
    }

    public T getBack(){
        return getLast();
    }

    public T getFront(){
        return getFirst();
    }

    public void removeFront(){
        removeFirst();
    }
    public void removeBack(){
        removeLast();
    }

    public T get(int pos){
        return internal.get(pos);
    }

    public int size(){
        return internal.size();
    }

    /**
     * clear removes cards progressively from the front
     */
    public void clear(){
        while(size() > 0){
            removeFirst();
        }
    }

    //makes items in the deck aware of their positions in the deck
    private void updateItemPositions(){
        for(int i=0; i<internal.size(); ++i){
            internal.get(i).setPositionWithinViewGroup(i);
        }
    }

    private void addFirst(T t) {
        internal.addFirst(t);
        updateItemPositions();
        listener.itemAddedFront(t);
    }

    private void addLast(T t) {
        internal.addLast(t);
        updateItemPositions();
        listener.itemAddedBack(t);
    }

    private T removeFirst() {
        T toRemove = internal.removeFirst();
        updateItemPositions();
        listener.itemRemovedFront(toRemove);
        return toRemove;
    }

    private T removeLast() {
        T toRemove = internal.removeLast();
        updateItemPositions();
        listener.itemRemovedBack(toRemove);
        return toRemove;
    }

    private T getFirst() {
        T getFirst = null;
        if(internal.size()>0){
            getFirst = internal.getFirst();
        }
        return getFirst;
    }

    private T getLast() {
        T getLast = internal.getLast();
        return getLast;
    }


    public interface DeckEventListener {
        void itemAddedFront(Object item);
        void itemAddedBack(Object item);
        void itemRemovedFront(Object item);
        void itemRemovedBack(Object item);
    }
}
