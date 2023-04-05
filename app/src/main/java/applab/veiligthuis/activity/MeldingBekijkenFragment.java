package applab.veiligthuis.activity;

import androidx.fragment.app.Fragment;

public class MeldingBekijkenFragment extends Fragment {

//    private MeldingBekijkenViewModel viewModel;
//
//    public static MeldingBekijkenFragment newInstance() {
//        return new MeldingBekijkenFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_melding_bekijken, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        MeldingVMFactory factory = new MeldingVMFactory(new MeldingRepositoryImpl());
//        viewModel = new ViewModelProvider(this, factory).get(MeldingBekijkenViewModel.class);
//
//        int id = requireArguments().getInt("melding_id");
//
//        TextView vw = view.findViewById(R.id.melding_bekijken_id);
//        TextView info = view.findViewById(R.id.melding_bekijken_info);
//
//        viewModel.getMeldingByIdLiveData(id).observe(getViewLifecycleOwner(), meldingDisplay -> {
//            vw.setText(""+meldingDisplay.getDisplayId());
//            info.setText(meldingDisplay.getDisplayMeldingInfo());
//        });
//    }
}