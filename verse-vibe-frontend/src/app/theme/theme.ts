import { createTheme } from '@mui/material';

const theme = createTheme({
  palette: {
    mode: 'light',
    background: {
      paper: '#ffffff',
    },
    text: {
      primary: '#121212',
    },
    primary: {
      main: '#1db954',
    },
    secondary: {
      main: '#121212',
    },
  },
  typography: {
    fontFamily: 'Arial, "Helvetica Neue", Helvetica, sans-serif',
    fontSize: 16,
  },
});

export default theme;
