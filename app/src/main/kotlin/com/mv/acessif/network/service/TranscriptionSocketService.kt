package com.mv.acessif.network.service

interface TranscriptionSocketService {
    // TODO private val socketEndpoint = "ws://BASE-URL/socketendpoint"

    // # Route that emits a custom Socket.IO event
    // @app.route('/custom_event')
    // def custom_event():
    //    # Emit the custom event to all connected clients in the '/chat' namespace
    //    socketio.emit('custom event', {'data': 42}, namespace='/chat')
    //    return "Custom event emitted!"

    // Se eu quiser receber coisas do app devo usar o on 'message'

    // # Connection event handler
    // @socketio.on('connect', namespace='/chat')
    // def handle_connect():
    //    print('Client connected')
    //    emit('connect', {'message': 'Connected to server'})

    // # Disconnection event handler
    // @socketio.on('disconnect', namespace='/chat')
    // def handle_disconnect():
    //    print('Client disconnected')
}
