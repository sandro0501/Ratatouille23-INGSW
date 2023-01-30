package com.example.ratatouille23.Views;

import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Bacheca;

public interface RecyclerViewAvvisoInterface {
    void onAvvisoClicked(int posizioneAvviso);

    void onOcchioAvvisoClicked(Bacheca avvisoScelto);
}
