import React, { useState } from 'react';
import { Button, Modal } from "react-bootstrap";
import { propTypes } from 'react-bootstrap/esm/Image';


export default function AuthenticationModal(props){
    function handleClose(){
        props.onHide(true)
    }

    return (
        <Modal show={props.show} onHide={handleClose} animation={false}>
            <Modal.Header closeButton>
            <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
            <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
                Close
            </Button>
            <Button variant="primary" onClick={handleClose}>
                Save Changes
            </Button>
            </Modal.Footer>
        </Modal>
    )
}