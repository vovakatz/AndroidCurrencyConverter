package com.vova.currencyconverter.activities.main;


public class MainPresenter implements IMainPresenter
{
    private IMainView _view;

    public MainPresenter(IMainView view)
    {
        _view = view;
    }

    public void convert(String amount)
    {
        if (validateInput(amount))
        {
            String test = "";
        }
    }

    public boolean validateInput(String amount)
    {
        if (true)
        {
            _view.displayError("You entered a wrong amount");
            return false;
        }
        else
            return true;
    }
}
