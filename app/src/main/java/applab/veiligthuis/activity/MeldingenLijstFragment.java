//package applab.veiligthuis.activity;
//
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
//import applab.veiligthuis.R;
//
//public class MeldingenLijstFragment extends Fragment implements RecyclerViewInterface {
//
////    private MeldingenLijstViewModel mViewModel;
////    private RecyclerView recyclerView;
////
////    public static MeldingenLijstFragment newInstance() {
////        return new MeldingenLijstFragment();
////    }
////
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
////                             @Nullable Bundle savedInstanceState) {
////        return inflater.inflate(R.layout.fragment_meldingen_lijst, container, false);
////    }
////
////    @Override
////    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
////        super.onViewCreated(view, savedInstanceState);
////        mViewModel = new ViewModelProvider(getActivity()).get(MeldingenLijstViewModel.class);
////
////        recyclerView = getView().findViewById(R.id.meldingenRecycler);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        MeldingenAdapter meldingAdapter = new MeldingenAdapter(new ArrayList<>(), this);
////        recyclerView.setAdapter(meldingAdapter);
////
////        mViewModel.getMeldingenLijst().observe(getViewLifecycleOwner(), meldingen -> {
////            //meldingAdapter.setMeldingenList(meldingen);
////            //meldingAdapter.notifyDataSetChanged();
////        });
////
////
////    }
////
////    @Override
////    public void onMeldingClick(int position) {
////        Bundle bundle = new Bundle();
////        bundle.putInt("melding_id", position);
////        FragmentManager fm  = getParentFragmentManager();
////        fm.beginTransaction()
////                .replace(R.id.fragmentContainerView, MeldingBekijkenFragment.class, bundle)
////                .setReorderingAllowed(true)
////                .addToBackStack("name")
////                .commit();
////    }
//}