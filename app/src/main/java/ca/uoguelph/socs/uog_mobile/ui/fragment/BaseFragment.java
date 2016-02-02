package ca.uoguelph.socs.uog_mobile.ui.fragment;

import android.support.v4.app.Fragment;
import butterknife.ButterKnife;
import ca.uoguelph.socs.uog_mobile.injection.HasComponent;

/**
 * Created by julianhorvat on 2016-01-22.
 */
public abstract class BaseFragment extends Fragment {

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @SuppressWarnings("unchecked") protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
