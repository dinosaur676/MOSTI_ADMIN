/*!
* Start Bootstrap - Scrolling Nav v5.0.6 (https://startbootstrap.com/template/scrolling-nav)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-scrolling-nav/blob/master/LICENSE)
*/

import React, { useState } from 'react';
import { Button } from "react-bootstrap";
import AutohideToastMessage from "../components/AutohideToastMessage"

export default function AboutPage(){
    const [messageColor, setMessageColor] = useState('info');
    const [toastShow, setToastShow] = useState(false);
    const [toastTitle, setToastTitle] = useState("");
    const [toaseContent, setToaseContent] = useState("");
    function showToastMessage(shown, title, content, color){
        setToastShow(shown)
        setToastTitle(title)
        setToaseContent(content)
        setMessageColor(color)
    }

    return (
        <>
        <section id="about">
            <div class="container px-4">
                <div class="row gx-4 justify-content-center">
                    <div class="col-lg-8">
                        <h2>About this page</h2>
                        <p class="lead">This is a great place to talk about your webpage. This template is purposefully unstyled so you can use it as a boilerplate or starting point for you own landing page designs! This template features:</p>
                        <ul>
                            <li>Clickable nav links that smooth scroll to page sections</li>
                            <li>Responsive behavior when clicking nav links perfect for a one page website</li>
                            <li>Bootstrap's scrollspy feature which highlights which section of the page you're on in the navbar</li>
                            <li>Minimal custom CSS so you are free to explore your own unique design options</li>
                        </ul>
                        
                        <Button variant="info" onClick={() => showToastMessage(true, "알림", "validation이나 crud결과 값등을 출력", 'Success')}>토스트 메세지 결과</Button>
                    </div>
                </div>
            </div>
        </section>
        <AutohideToastMessage
            show={toastShow}
            onHide={() => setToastShow(false)}
            variant={messageColor}
            title={toastTitle}
            content={toaseContent}
        >
        </AutohideToastMessage>
        </>
    )
}