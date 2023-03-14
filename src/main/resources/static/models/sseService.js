const evtSource = new EventSource("/api/sse/redis/stream-flux", {
  withCredentials: true,
});

export {evtSource};
