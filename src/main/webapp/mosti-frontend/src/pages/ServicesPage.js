/*!
* Start Bootstrap - Scrolling Nav v5.0.6 (https://startbootstrap.com/template/scrolling-nav)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-scrolling-nav/blob/master/LICENSE)
*/

import React, { useState } from 'react';
import { Button } from "react-bootstrap";
import AuthenticationModal from '../components/AuthenticationModal';

export default function ServicesPage(){
    const [modalShow, setModalShow] = useState(false);

    return (
        <>
        <section class="bg-light" id="services">
            <div class="container px-4">
                <div class="row gx-4 justify-content-center">
                    <div class="col-lg-8">
                        <h2>학생증 인증</h2>
                        <p class="lead">학생증 인증하기</p>
                        <Button variant="info" onClick={() => setModalShow(true)}>학생증 인증하기</Button>{' '}
                    </div>
                </div>
            </div>
        </section>
        <AuthenticationModal
            show={modalShow}
            onHide={() => setModalShow(false)}
        ></AuthenticationModal>
        </>
    )
}