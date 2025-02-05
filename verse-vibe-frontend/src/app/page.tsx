import Header from './components/common/header/Header';
import { Fragment } from 'react';
import ThemeWrapper from './theme/ThemeWrapper';
import Footer from './components/common/footer/Footer';
import Main from './components/main/Main';
import { Toaster } from 'react-hot-toast';

export default function Home() {
  return (
    <Fragment>
      <ThemeWrapper>
        <Header />
        <Main />
        <Footer />
        <Toaster />
      </ThemeWrapper>
    </Fragment>
  );
}
