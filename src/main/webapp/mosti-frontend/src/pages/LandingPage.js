/*!
* Start Bootstrap - Scrolling Nav v5.0.6 (https://startbootstrap.com/template/scrolling-nav)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-scrolling-nav/blob/master/LICENSE)
*/

import Header from '../components/Header';
import Navbar from '../components/Navbar';
import AboutPage from './AboutPage';
import ContactPage from './ContactPage';
import ServicesPage from './ServicesPage';
import Footer from '../components/Footer';
import '../styles/css/styles.css';

export default function LandingPage(){

    return (
        <>
        <Navbar></Navbar>
        <Header></Header>
        <AboutPage></AboutPage>
        <ServicesPage></ServicesPage>
        <ContactPage></ContactPage>
        <Footer></Footer>
        </>
    )
}