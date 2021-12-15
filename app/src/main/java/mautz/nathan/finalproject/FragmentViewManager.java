package mautz.nathan.finalproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

public class FragmentViewManager
{
    public FragmentViewManager(FragmentContainerView view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        active_fragment = null;
    }

    public void setActiveFragment(Fragment fragment)
    {
        if(active_fragment != null)
        {
            fragmentManager.beginTransaction().remove(active_fragment).commitNow();

        }
        fragmentManager.beginTransaction().add(view.getId(), fragment).commitNow();
        active_fragment = fragment;


    }

    private FragmentContainerView view;
    private Fragment active_fragment;
    private FragmentManager fragmentManager;
}
