(define not (lambda (p) (cond (p nil)
                              (t t))))

(not t)

(not nil)

(not ())

(not (quote a))

(not (eq (quote a) (quote a)))

(not (atom (quote a)))

(not (atom (quote (a b))))
