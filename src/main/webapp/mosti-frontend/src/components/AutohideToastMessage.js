import React from 'react';
import Button from 'react-bootstrap/Button';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';

function AutohideToastMessage(props) {

  return (
    <>
        <div>
        <ToastContainer className="p-3" position='bottom-end'>
        <Toast bg={props.variant.toLowerCase()} onClose={props.onHide} show={props.show} delay={3000} autohide>
            <Toast.Header closeButton={false} >
              <strong className="me-auto">{props.title}</strong>
              {/* <small>11 mins ago</small> */}
              <Button variant='Light' size="sm" onClick={props.onHide}>X</Button>
            </Toast.Header>
            <Toast.Body>{props.content}</Toast.Body>
          </Toast>
        </ToastContainer>
        </div>
    </>
  );
}

export default AutohideToastMessage;