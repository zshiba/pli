(define list (lambda (x y) (cons x (cons y ()))))

(list (quote a) (quote b))

(list (quote a) ())

(list (quote a) (quote (b c)))

(cons (quote a) (quote b))

(cons (quote a) ())

(cons (quote a) (quote (b c)))
