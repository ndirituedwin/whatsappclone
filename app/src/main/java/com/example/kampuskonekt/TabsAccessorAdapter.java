package com.example.kampuskonekt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.kampuskonekt.Fragments.ChatsFragment;
import com.example.kampuskonekt.Fragments.ContactsFragment;
import com.example.kampuskonekt.Fragments.GroupsFragment;
import com.example.kampuskonekt.Fragments.PhoneContacts;
import com.firebase.ui.auth.ui.phone.CheckPhoneNumberFragment;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fm) {
        super(fm);
    }

    public TabsAccessorAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                //ChatsFragment chatsFragment=new ChatsFragment();
                return new ChatsFragment();
            case 1:
                return new GroupsFragment();
            case 2:
                return new ContactsFragment();

            case 3:
                //ContactsPhoneFragment c=new ContactsPhoneFragment();
                return new PhoneContacts();

            default:
                return null;

        }
    }



    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        switch (position){
            case 0:
                //ChatsFragment chatsFragment=new ChatsFragment();
                return "Chats";
            case 1:
                return "Groups";
            case 2:
                return "Contacts";
            case 3:
                return "Phone contacts";

            default:
                return null;

        }

    }
}
